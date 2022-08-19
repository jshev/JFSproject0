public class Transaction {
    private int id;
    private String type;
    private String madeBy;
    private int amount;
    private String fromAcc;
    private String toAcc;

    public Transaction(){
    }

    public Transaction(int id, String type, String madeBy, int amount, String fromAcc, String toAcc) {
        this.id = id;
        this.type = type;
        this.madeBy = madeBy;
        this.amount = amount;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromAcc() {
        return fromAcc;
    }

    public void setFromAcc(String fromAcc) {
        this.fromAcc = fromAcc;
    }

    public String getToAcc() {
        return toAcc;
    }

    public void setToAcc(String toAcc) {
        this.toAcc = toAcc;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", madeBy='" + madeBy + '\'' +
                ", amount=" + amount +
                ", fromAcc='" + fromAcc + '\'' +
                ", toAcc='" + toAcc + '\'' +
                '}';
    }
}
