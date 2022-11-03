package org.sanjida.matillion.response;

import java.util.Objects;

public class MatillionResponse {
    private Boolean success = false;
    private String msg = "";
    private Integer id = 0;

    public MatillionResponse(){

    }

    public MatillionResponse(Boolean success, String msg, Integer id){
        this.success = success;
        this.msg = msg;
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatillionResponse that = (MatillionResponse) o;
        return Objects.equals(success, that.success) && Objects.equals(msg, that.msg) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, msg, id);
    }

    @Override
    public String toString() {
        return "ValidationResponse{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                '}';
    }
}
