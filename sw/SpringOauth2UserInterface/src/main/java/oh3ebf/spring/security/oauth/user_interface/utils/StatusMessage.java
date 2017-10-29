/**
 * Software:
 * Module: StatusMessage class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 23.12.2016
 */
package oh3ebf.spring.security.oauth.user_interface.utils;

import java.util.ArrayList;
import java.util.List;

public class StatusMessage<T> {

    private String status;
    private List<T> response;
    public static final String MESSAGE_OK = "TXT_OK";
    public static final String MESSAGE_EMPTY = "TXT_EMPTY";
    public static final String MESSAGE_FAIL = "TXT_FAIL";

    public StatusMessage() {
        this.status = "";
        this.response = new ArrayList<>();
    }

    public StatusMessage(String status) {
        this.status = status;
    }

    /**
     * Function returns status message
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Function set status message
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the response
     */
    public List<T> getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(List<T> response) {
        this.response = response;
    }
}
