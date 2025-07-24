package br.com.ronaldoamorim.wallet_challenge.wallet;

public enum WalletType {
    CUSTOMER(1),
    MERCHANT(2);

    private final int value;

    WalletType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
