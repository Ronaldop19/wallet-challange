package br.com.ronaldoamorim.wallet_challenge.Authorization;

public record Authorization(Data data) {
    public boolean isAuthorized() {
        return data !=null && data.authorization();
    }

    record Data(boolean authorization) {
    }
}
