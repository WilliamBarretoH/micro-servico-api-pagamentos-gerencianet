package br.com.gerenciapedidos.payments.domain.response;

public class ResponseBillet {

    private Integer code;
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Pdf{
        private String charge;

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }
    }

    public class Data{
        private Integer total;
        private Pdf pdf;
        private Integer charge_id;
        private String link;
        private String expire_at;
        private String payment;
        private String barcode;
        private String status;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Pdf getPdf() {
            return pdf;
        }

        public void setPdf(Pdf pdf) {
            this.pdf = pdf;
        }

        public Integer getCharge_id() {
            return charge_id;
        }

        public void setCharge_id(Integer charge_id) {
            this.charge_id = charge_id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getExpire_at() {
            return expire_at;
        }

        public void setExpire_at(String expire_at) {
            this.expire_at = expire_at;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
