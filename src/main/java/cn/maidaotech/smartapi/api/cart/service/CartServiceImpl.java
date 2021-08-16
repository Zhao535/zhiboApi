package cn.maidaotech.smartapi.api.cart.service;

import cn.maidaotech.smartapi.api.cart.entity.CartErrors;
import cn.maidaotech.smartapi.api.cart.entity.CartPayload;
import cn.maidaotech.smartapi.api.cart.entity.CartWarpOption;
import cn.maidaotech.smartapi.api.cart.entity.PayloadType;
import cn.maidaotech.smartapi.api.cart.model.Cart;
import cn.maidaotech.smartapi.api.cart.repository.CartRepository;
import cn.maidaotech.smartapi.api.merchant.model.Merchant;
import cn.maidaotech.smartapi.api.merchant.service.MerchantService;
import cn.maidaotech.smartapi.api.product.model.Product;
import cn.maidaotech.smartapi.api.product.service.ProductService;
import cn.maidaotech.smartapi.api.secKill.entity.SecKillWrapOption;
import cn.maidaotech.smartapi.api.secKill.model.SecKill;
import cn.maidaotech.smartapi.api.secKill.service.SecKillService;
import cn.maidaotech.smartapi.common.cache.CacheOptions;
import cn.maidaotech.smartapi.common.cache.KvCacheFactory;
import cn.maidaotech.smartapi.common.cache.KvCacheWrap;
import cn.maidaotech.smartapi.common.context.UserContexts;
import cn.maidaotech.smartapi.common.exception.ArgumentServiceException;
import cn.maidaotech.smartapi.common.exception.DataNotFoundServiceException;
import cn.maidaotech.smartapi.common.exception.ServiceException;
import cn.maidaotech.smartapi.common.model.Constants;
import cn.maidaotech.smartapi.common.utils.CollectionUtils;
import com.sunnysuperman.kvcache.RepositoryProvider;
import com.sunnysuperman.kvcache.converter.BeanModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService, CartErrors {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SecKillService secKillService;

    @Autowired
    private KvCacheFactory kvCacheFactory;

    private KvCacheWrap<Integer, Cart> cartCache;

    @PostConstruct
    public void init() {
        cartCache = kvCacheFactory.create(new CacheOptions("cart", 1, Constants.CACHE_REDIS_EXPIRE),
                new RepositoryProvider<Integer, Cart>() {

                    @Override
                    public Cart findByKey(Integer id) throws ServiceException {
                        return cartRepository.findById(id).orElse(null);
                    }

                    @Override
                    public Map<Integer, Cart> findByKeys(Collection<Integer> ids) throws ServiceException {
                        List<Integer> idlist = new ArrayList<>(ids);
                        List<Cart> carts = cartRepository.findAllByIdIn(idlist);
                        return carts.stream().collect(Collectors.toMap(Cart::getId, c -> c));
                    }
                }, new BeanModelConverter<>(Cart.class));
    }

    private Cart findById(Integer id) throws Exception {
        if (Objects.isNull(id)) {
            throw new ArgumentServiceException("id");
        }
        Cart cart = cartCache.findByKey(id);
        return cart;
    }

    private Cart getById(Integer id) throws Exception {
        Cart cart = findById(id);
        if (Objects.isNull(cart)) {
            throw new DataNotFoundServiceException();
        }
        return cart;
    }

    @Override
    public void removeList(List<Integer> ids) throws Exception {
        List<Cart> carts = cartRepository.findAllByIdIn(ids);
        cartRepository.deleteAll(carts);
    }

    @Override
    public void removeOne(Integer id) throws Exception {
        Integer userId = UserContexts.requestUserId();
        Cart cart = writeBle(id);
        cartRepository.delete(cart);
    }

    private Cart writeBle(Integer id) throws Exception {
        Integer userId = UserContexts.requestUserId();
        Cart cart = getById(id);
        if (!cart.getUserId().equals(userId)) {
            throw new ServiceException(ERR_PERMISSION_DENIED);
        }
        return cart;
    }

    @Override
    public Cart save(Cart cart) throws Exception {
        Long now = System.currentTimeMillis();
        if (Objects.isNull(cart)) {
            throw new ServiceException(ERR_CART_NOT_NULL);
        }
        if (Objects.isNull(cart.getNum()) || cart.getNum() < 0) {
            throw new ServiceException(ERR_CART_BUY_NUM_NOT_NULL);
        }
        if (Objects.isNull(cart.getMerchantId())) {
            throw new ServiceException(ERR_CART_MERCHANT_ID_NOT_NULL);
        }
        if (Objects.isNull(cart.getProductSno())) {
            throw new ServiceException(CartErrors.ERR_CART_PRODUCT_SPEC_NOT_NULL);
        }
        if (Objects.isNull(cart.getProductId())) {
            throw new ServiceException(CartErrors.ERR_CART_PRODUCT_ID_NOT_NULL);
        }
        if (Objects.isNull(cart.getCartPayload())) {
            CartPayload payload = new CartPayload();
            payload.setType(PayloadType.NORMAL.value());
            cart.setCartPayload(payload);
        } else {
            if (cart.getCartPayload().getType() == PayloadType.SECKILL.value()) {
                Integer secKillId = cart.getCartPayload().getId();
                SecKill secKill = secKillService.secKill(secKillId, SecKillWrapOption.getAdmList());
                if (Objects.isNull(secKill) || secKill.getEndAt() < now) {
                    throw new ServiceException(ERR_CART_SECKILL_ID_ERROR);
                }
            }
        }
        Integer userId = UserContexts.requestUserId();
        Cart exist = cartRepository.findByUserIdAndProductIdAndProductSno(userId, cart.getProductId(),
                cart.getProductSno());
        if (Objects.isNull(exist)) {
            cart.setUserId(userId);
            cart.setCreatedAt(now);
            cartRepository.save(cart);
            return cart;
        } else {
            if (exist.getNum() <= 0) {
                cartRepository.delete(exist);
                cartCache.removeSafely(exist.getId());
            } else if (exist.getProductSno().equals(cart.getProductSno())) {
                exist.setNum(exist.getNum() + cart.getNum());
                cartRepository.save(exist);
                cartCache.removeSafely(exist.getId());
            }
            return exist;
        }
    }

    @Override
    public Cart item(Integer id) throws Exception {
        return getById(id);
    }

    @Override
    public List<Cart> items(Integer userId) throws Exception {
        List<Cart> carts = cartRepository.findByUserId(userId);
        wrap(carts, CartWarpOption.getInstance());
        return carts;
    }

    @Override
    public List<Cart> findCartList(List<Integer> ids) throws Exception {
        List<Cart> carts = cartRepository.findAllByIdIn(ids);
        wrap(carts, CartWarpOption.getInstance());
        return carts;
    }

    @Override
    public void updateBuyNum(Integer id, Integer number) throws Exception {
        Cart cart = writeBle(id);
        cart.setNum(number);
        cartRepository.save(cart);
        cartCache.removeSafely(id);
    }

    @Override
    public void updateSpecs(Integer id, String productSno) throws Exception {
        Cart cart = writeBle(id);
        Cart newCart = new Cart();
        newCart.copy(cart, productSno);
        save(newCart);
        removeOne(id);
        cartCache.removeSafely(id);
    }

    private void wrap(List<Cart> carts, CartWarpOption option) throws Exception {
        if (CollectionUtils.isEmpty(carts)) {
            return;
        }
        List<Integer> merchantIds = carts.stream().map(Cart::getMerchantId).collect(Collectors.toList());
        List<Integer> productIds = carts.stream().map(Cart::getProductId).collect(Collectors.toList());
        List<Integer> secKillIds = carts.stream().map(Cart::getCartPayload)
                .filter(it -> it.getType() == PayloadType.SECKILL.value()).map(CartPayload::getId)
                .collect(Collectors.toList());
        Map<Integer, Merchant> merchantMap = merchantService.findByIdIn(merchantIds).stream()
                .collect(Collectors.toMap(Merchant::getId, it -> it));
        Map<Integer, Product> productMap = productService.findByIdIn(productIds).stream()
                .collect(Collectors.toMap(Product::getId, item -> item));
        Map<Integer, SecKill> secKillMap = secKillService.findByIdIn(secKillIds).stream()
                .collect(Collectors.toMap(SecKill::getId, item -> item));

        for (Cart cart : carts) {
            cart.setMerchant(merchantMap.get(cart.getMerchantId()));
            cart.setProduct(productMap.get(cart.getProductId()));
            if (option.isWithSecKill() || cart.getCartPayload().getType() == PayloadType.SECKILL.value()) {
                SecKill secKill = secKillMap.get(cart.getCartPayload().getId());
                cart.setSecKill(secKill);
            }
        }
    }

    @Override
    public void saveAll(List<Cart> cartList) throws Exception {
        for (Cart cart : cartList) {
            if (Objects.isNull(cart)) {
                throw new ServiceException(ERR_CART_NOT_NULL);
            }
            if (Objects.isNull(cart.getNum()) || cart.getNum() < 0) {
                throw new ServiceException(ERR_CART_BUY_NUM_NOT_NULL);
            }
            if (Objects.isNull(cart.getMerchantId())) {
                throw new ServiceException(ERR_CART_MERCHANT_ID_NOT_NULL);
            }
            if (Objects.isNull(cart.getProductSno())) {
                throw new ServiceException(CartErrors.ERR_CART_PRODUCT_SPEC_NOT_NULL);
            }
            if (Objects.isNull(cart.getProductId())) {
                throw new ServiceException(CartErrors.ERR_CART_PRODUCT_ID_NOT_NULL);
            }
            Integer userId = UserContexts.requestUserId();
            Long now = System.currentTimeMillis();
            Cart exist = cartRepository.findByUserIdAndProductIdAndProductSno(userId, cart.getProductId(),
                    cart.getProductSno());
            cart.setUserId(userId);
            cart.setCreatedAt(now);
            if (Objects.isNull(exist)) {
                cart.setUserId(userId);
                cart.setCreatedAt(now);
            } else {
                if (exist.getNum() <= 0) {
                    cartRepository.delete(exist);
                    cartCache.removeSafely(exist.getId());
                } else if (exist.getProductSno().equals(cart.getProductSno())) {
                    exist.setNum(exist.getNum() + cart.getNum());
                    cartCache.removeSafely(exist.getId());
                }
                cart.setNum(exist.getNum());
                cart.setId(exist.getId());
            }
        }
        cartRepository.saveAll(cartList);
    }
}
