package com.payment.alipay;

public class Contants {
    //请填写您的AppId
    public static final String appId = "2016080200148359";
    //应用私钥
    public static final String merchantPrivateKey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCBlOp/Tr+X4+SBzKIvEOQAAeOmT1y3uWW1162HCJQIZjI7T43FBnuZ5+BuqAPq8k5MigDtK843kUCf1PoqRUOTBqPZvT4btPsWbAu+ko5q3D7HZXU0j2SIFz1CWUOL2SB6fbsoUwhmL6tP13M8S4lO6Ll5huY/nOiC4hiV0VX+KobNcrRaW29YvKdHVNISev35BK9SLQ9l9jDWnAggCH7UYwnrsDp8JXwNRCTX1qWYXRnfqygjEu1kdFJ9+q+AImqtrNp0+JnhzbO90cHQhMCVFMDSsUw7lWYzTeVAixDXDq4uveyAJGaLL9T2Ikoeg64miyWUwJMACoMUJS0uPnXVAgMBAAECggEAaCmVinp5xpX5VGFzKZRRQrRCE6kN5d3RJahWpApAPLQxwr2DP5KjfqcHIMxMQes/B4WTkxK4dLB9Gz3vTlVZKXGX+mqF523GvFJj6nASrVGX4V7sJTDy5faQVqo8gskps20oKhT81m+RLOAKkLhDirvGhCrtIMt3l+Y5Twz0LBdIajPKEON7N0BxuYUGsLko+XFjLdCPX3WHZDkF1jQ9Gehd66UoWmaU0a86xey3f4TFNs8+uK9QwLvH1fRmKBhOCLWv15wMPrC3uotloFc/r2gOkeekJiNTdHMm6ilFMEbg1suxHUmv9eSg1kVN7jHCHqnYVrVBr2tr0slpdAIqYQKBgQDW5TxiJ/JGWz4moxXARprW4vmh3/DuGJAmMokbloPRMvPS59fwm43ZRRxxviiPcxMu8uqfgOIz3lnTYQIw3fnf7JPAePeGwOsA3f9Nhl7GfWII7HOjNO7eUzSIlULG33AjJZJp/DAHXYY0NmeaDuFlGJIxhw2wc5m7bDsWPXwNCQKBgQCaXiFbLcC2S1HW68sGb62gL2mb+jaBvh0covlC0LzhidRykT6QrnP2MBze3RhUyFxEqUWO6E1W/SIAaNSIxwyJVJVUkD8o2zTNPsUfvdepgB+yOoWb26RmQka0ILC569H9SaH2whVcfj5pHlx6Ez27fMcMAgJJR1a96qXd5zjhbQKBgFOAvBgAIbqJFft5f6g5R02c4RevpzU9wjW1iWV49TvDKs+XW2eZamM04bqpA6XKN3gU2GqmcD9rdUgX9/v5JXGHwloWJ0jrieXKXbxILGfArOl+x+hxVyh4+H7iURJqHG7r2JrqbddO1ilPwq4wfhEEnTo1mnIoWWJkygj4V03RAoGAbnUyqxrHDepBameZBEb/V2L1z+2v+RC/phEcCWpx7XUBtuUlfsPGKIBu+C3+zhP8qgjxw/uH89n37ZEVm7XKy63hmPwKpcKNOtxSk471dc0/YPotRsRZE+8SxqupqbALt5FrurVQMboIEJHwHE4OW01C5N2/Iw5QDF+ORoXAltUCgYBbEgiNQ5fX35bOCD7HBkvcIjL2boaVOhPXZjvAqj8LYnOOgFXIZ7+00URNjOhIhC00P1pgdNX/Ec8RdC2HSW5qtFVUymLNDmksXkkBJBy+jsA1Vb59y3+MWyVwSQXAFZYM/9nsM+XN8b9Onit5mZWYClpJWjp1ggB+I5zKYrS/7g==";
    //支付宝公钥
    public static final String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1+SJjPtqfM71ZFM1OYMvFh1k5/8iN1dZnUGHbqRQX1vDiuvlzBDQn5n2sJZqo3/8nQbqWcasA13guFMtRDHp7SesbhOV8TkKrnKxXQEmeOOzireWzSogfX1noJUlcvzPhT1IdKkllHPDF2f30R6Fvb3M5H5hPRpIjiKH0RxHT/U+cea7rIw1hv2l8cCyK8D9t+nEPb6DL5Mx01pLFEZAK53dobWhiferklSaTB8JJhL2ZIcX8TpEwW+2EYAZ0m8voe/ReqejH1LXAf9s2u99jNEq57LQVZgjSPjaWWiZWk8HC6BN6b+Il7ob/ZnWfziUGie4qDbNh/j5rS16HcWWjwIDAQAB";
    //AES密钥
    public static final String encryptKey="CsctZF2waO2kzS5TTmt4Tw==";

    //注：如果采用非证书模式，则无需赋值下面的三个证书路径，改为赋值支付宝公钥字符串即可
    //应用公钥证书文件路径
    public static final String merchantCertPath="";
    //支付宝公钥证书文件路径
    public static final String alipayCertPath="";
    //支付宝根证书文件路径
    public static final String alipayRootCertPath="";



    public static final String protocol="https";

    //正式网关
    //public static final String gatewayHost="openapi.alipay.com";
    //沙箱网关
    public static final String gatewayHost="openapi.alipaydev.com";

    public static final String signType = "RSA2";

    public static final String notifyUrl = "";

}
