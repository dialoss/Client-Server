package Common.Models;

public enum OrganizationType {
    COMMERCIAL,
    PUBLIC,
    GOVERNMENT,
    OPEN_JOINT_STOCK_COMPANY;

    public static Object fromString(String data) {
        return data.toUpperCase();
    }
}