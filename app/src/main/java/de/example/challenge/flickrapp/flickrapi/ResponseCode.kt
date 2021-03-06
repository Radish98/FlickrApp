package de.example.challenge.flickrapp.flickrapi

enum class ResponseCode(val errorCode: Int) {
    UNKNOWN_EXCEPTION(0),
    TOO_MANY_TAGS(1),
    UNKNOWN_USER(2),
    PARAMETERLESS_SEARCH_DISABLED(3),
    NO_PERMISSION(4),
    USER_DELETED(5),
    API_UNAVAILABLE(10),
    NO_VALID_TAGS(11),
    EXCEEDED_MAX_TAGS(12),
    SEARCH_ONLY_WITHIN_CONTACTS(17),
    ILLOGICAL_ARGUMENTS(18),
    INVALID_KEY(100),
    SERVICE_UNAVAILABLE(105),
    OPERATION_FAILED(106),
    RESPONSE_FORMAT_NOT_FOUND(111),
    METHOD_NOT_FOUND(112),
    INVALID_SOAP(114),
    INVALID_XML_RPC(115),
    BAD_URL(116),
    SERVER_UNAVAILABLE(500),
    BAD_REQUEST(400),
    URL_CHANGED(300),
    RESPONSE_OK(200),
    NOTHING_FOUND(210),
    SOCKET_TIMEOUT(901),
    UNKNOWN_SERVICE_EXCEPTION(902),
    SOCKET_EXCEPTION(903),
    IO_EXCEPTION(904),
    CALL_CANCELLED(905),
    NO_NETWORK_CONNECTION(906)
}