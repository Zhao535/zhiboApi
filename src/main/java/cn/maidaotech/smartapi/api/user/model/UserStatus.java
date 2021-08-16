package cn.maidaotech.smartapi.api.user.model;

public enum UserStatus {
    NORMAL(1),FORBID(2);

    private byte value;

    UserStatus(int value) {
        this.value = (byte)value;
    }

    public byte value (){
        return this.value;
    }

    public static UserStatus find (Byte value){
        if (value == null) {
            return null;
        }
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.value() == value.byteValue()) {
                return userStatus;
            }
        }
        return null;


    }
}
