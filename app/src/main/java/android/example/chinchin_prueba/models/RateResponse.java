package android.example.chinchin_prueba.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateResponse implements Serializable, Parcelable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rates")
    @Expose
    private Rates rates;
    public final static Parcelable.Creator<RateResponse> CREATOR = new Creator<RateResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RateResponse createFromParcel(Parcel in) {
            return new RateResponse(in);
        }

        public RateResponse[] newArray(int size) {
            return (new RateResponse[size]);
        }

    }
            ;
    private final static long serialVersionUID = -8974350029308928151L;

    protected RateResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.base = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.rates = ((Rates) in.readValue((Rates.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public RateResponse() {
    }

    /**
     *
     * @param date
     * @param success
     * @param rates
     * @param base
     */
    public RateResponse(Boolean success, String base, String date, Rates rates) {
        super();
        this.success = success;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public RateResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public RateResponse withBase(String base) {
        this.base = base;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RateResponse withDate(String date) {
        this.date = date;
        return this;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public RateResponse withRates(Rates rates) {
        this.rates = rates;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(base);
        dest.writeValue(date);
        dest.writeValue(rates);
    }

    public int describeContents() {
        return 0;
    }

}
