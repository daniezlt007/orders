package br.com.deadsystem.api.orders.model;

public enum StatusEnum {

    PENDING("PENDING"),
    CANCELED("CANCELED"),
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED");

    private String status;

    StatusEnum(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusEnum buscarPorString(String status) {
        for (StatusEnum statusEnumBusca : StatusEnum.values()) {
            if (statusEnumBusca.status.equalsIgnoreCase(status)) {
                return statusEnumBusca;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }

}
