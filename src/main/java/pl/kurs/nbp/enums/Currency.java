package pl.kurs.nbp.task.enums;

public enum Currency {
    USD("usd"), GBP("gbp"), EUR("eur");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
