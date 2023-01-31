package mainpackage;

public class Const {
    public static final String PASSENGERS_TABLE = "passenger";
    //пассажиры
    public static final String PASSENGER_ID = "id_passenger";
    public static final String PASSENGER_NAME = "name_passenger";
    public static final String PASSENGER_TELEPHONE = "telephone";
    public static final String PASSENGER_MAIL = "mail";
    public static final String PASSENGER_PASSPORT = "passport";

    public static final String BUSES_TABLE = "bus";
    //автобусы
    public static final String BUS_ID = "id_bus";
    public static final String BUS_NUMBER = "bus_number";
    public static final String DATE_BEGIN = "date__begin";
    public static final String DATE_END = "date__end";
    public static final String SEATS_NUMBER = "seats_number";
    public static final String BUS_TYPE_ID = "type_id";

    public static final String TICKETS_TABLE = "ticket";
    //билеты
    public static final String TICKET_ID = "id_ticket";
    public static final String TICKET_PASSENGER_ID = "passenger_id";
    public static final String PLACE_NUMBER = "place_number";
    public static final String PRICE = "price";
    public static final String DATE_BOOKING = "date_booking";
    public static final String TICKET_FLIGHT_ID = "flight_id";


    public static final String FLIGHTS_TABLE = "flight";
    //рейсы
    public static final String FLIGHT_ID = "id_flight";
    public static final String FLIGHT_NUMBER = "number_flight";
    public static final String ADRESS_DEPARTURE= "adress_departure";
    public static final String ADRESS_DESTINATION = "adress_destination";
    public static final String DATE_DEPARTURE = "date_departure";
    public static final String DATE_DESTINATION = "date_destination";
    public static final String FLIGHT_BUS_ID = "bus_id";
    public static final String STATUS = "status";

    public static final String BUS_TYPE_TABLE = "bus_type";
    //типы автобусов
    public static final String TYPE_ID = "id_type";
    public static final String TYPE_NAME = "name_type";

    public static final String STATUS_TABLE = "status_table";
    public static final String STATUS_ID = "id_status";
    public static final String STATUS_NAME = "name_status";

    public static final String ADRESS_TABLE = "adress";
    public static final String ADRESS_ID = "id_adress";
    public static final String ADRES_NAME = "name_adress";
}
