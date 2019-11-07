package com.qf.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AliPayUtil {

    private static AlipayClient alipayClient = null;

    static {
        //初始化alipay的核心对象
        alipayClient = new DefaultAlipayClient(
                " https://openapi.alipaydev.com/gateway.do",
                "2016101300673340",
                "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKq2p+ZJW1C3ncd30k6hTWy0q0zmQa5tORgB5SfRtyU9In2OQ7LuObOoYgQQgF7p7d/jNoMtnyOCHSi+1NHNENtcxZZRR2Bb1NgR7ULLbYZ542ZLgmqntkN/g7N2HeXgaprKPEwdw2pWo8PU7v3fkEiNX3ulQ8Hq3mPejRuEDn5VAgMBAAECgYAdIJk5M57/ALVNCWa6v87kE1d/AXKHfl9Vd01QI/opwmqWHaXza64wtzQyGVat2yAZKT1t2GTkvBiZkSXivih33PoNUljL345FC8ydS97NKO5XVq4EHx5d6N+qdWtaCHzOGWHcPfNoYMBs/JyKU+VMUetuEb2tJR0Ig/nzXY0DgQJBAPTw8z+rnvQY9uAaO4ePB/2C3pwXepyfLWY8bmI72twv3PLxNcqvlVQ6lmHUfd92Q0b38CQdCSKeTdlasTOqMRECQQCya8ajBys2gjgNef3eFJd9/bavnZtDlMvZI6mC7EQp2RAGo47Xr+pXyW38nYX9FpTiOfDCrfV6lwux5es34vkFAkEA6/meP3712jCa1vgu1cBkcEW+dR7hjzaDJHWf2p/TkEHEWYEs06Io+UagTovK4Jgs5JhFEGWwwVtFoNsQ0LdsIQJBAKAQRDF5evD5vaOJb5DOVGH5PO0rrWDhmkcA0U/c+gG488Gg9cLV1JxQ3tUj1FKK8aJKbI2aVoFWBG3iYDTQ8KkCQQCvK8r+DfrSBqK7ZFJift0Rr2hOiMLmAwB4SqjbOwWqznjCAZuzVF24LGEs+W4LpoIrFsZToUifPmYVXpYt8Sil",
                "json",
                "UTF-8",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB",
                "RSA");
    }

    public static AlipayClient getAlipayClient(){
        return alipayClient;
    }
}
