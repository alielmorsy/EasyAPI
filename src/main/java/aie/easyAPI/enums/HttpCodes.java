package aie.easyAPI.enums;

public class HttpCodes {
    private HttpCodes() {
    }

    //200
    public static final int OK = 200;
    public static final int Created = 201;
    public static final int Accepted = 202;
    public static final int NonAuthInformation = 203;
    public static final int NoContent = 204;
    public static final int ResetContent = 205;
    public static final int PartialContent = 206;
    //300
    public static final int MultipleChoice = 300;
    public static final int MovedPermanently = 301;
    public static final int Found = 302;
    public static final int SeeOther = 303;
    public static final int NotModified = 304;
    public static final int TemporaryRedirect = 307;
    public static final int PermanentRedirect = 308;

    //400
    public static final int BadRequest = 400;
    public static final int Unauthorized = 401;
    public static final int PaymentRequired = 402;
    public static final int Forbidden = 403;
    public static final int NotFound = 404;
    public static final int MethodNotAllowed = 405;
    public static final int NotAcceptable = 406;
    public static final int UpgradeRequired = 407;
    public static final int TooManyRequests = 408;


    //500


}
