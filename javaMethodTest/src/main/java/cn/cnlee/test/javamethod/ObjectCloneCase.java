package cn.cnlee.test.javamethod;

import java.io.Serializable;

import cn.cnlee.test.javamethod.util.SerializationUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ObjectCloneCase {

    public static void main(String[] args) {
        Bean bean1 = new Bean(1, 1, "result1", "msg1");
        Bean bean2 = SerializationUtils.clone(bean1);
        bean2.setId(2);
        bean2.setMsg("msg2");
        System.err.print(bean1 + "\n" + bean2);
    }

    @Builder
    @ToString
    static class Bean implements Serializable {
        private int id;
        private int status;
        private String result;
        private String msg;

        public Bean(int id, int status, String result, String msg) {
            this.id = id;
            this.status = status;
            this.result = result;
            this.msg = msg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
