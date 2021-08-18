package com.example.showerforfriends;

public class Usage {
    private Integer time_count;
    private Float total_amount;
    private String time_stamp;

    public Usage(Integer time_count, Float total_amount, String time_stamp)
    {
        this.time_count = time_count;
        this.total_amount = total_amount;
        this.time_stamp = time_stamp;
    }

    public Integer getTime_count() {
        return time_count;
    }

    public Float getTotal_amount() {
        return total_amount;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_count(Integer time_count) {
        this.time_count = time_count;
    }

    public void setTotal_amount(Float total_amount) {
        this.total_amount = total_amount;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }
}
