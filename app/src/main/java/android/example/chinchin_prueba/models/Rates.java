

package android.example.chinchin_prueba.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates implements Serializable, Parcelable
{

    @SerializedName("BTC")
    @Expose
    private Double bTC;
    @SerializedName("EUR")
    @Expose
    private Double eUR;
    @SerializedName("USD")
    @Expose
    private Integer uSD;
    public final static Parcelable.Creator<Rates> CREATOR = new Creator<Rates>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Rates createFromParcel(Parcel in) {
            return new Rates(in);
        }

        public Rates[] newArray(int size) {
            return (new Rates[size]);
        }

    }
            ;
    private final static long serialVersionUID = 106873869906011084L;

    protected Rates(Parcel in) {
        this.bTC = ((Double) in.readValue((Double.class.getClassLoader())));
        this.eUR = ((Double) in.readValue((Double.class.getClassLoader())));
        this.uSD = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Rates() {
    }

    /**
     *
     * @param bTC
     * @param eUR
     * @param uSD
     */
    public Rates(Double bTC, Double eUR, Integer uSD) {
        super();
        this.bTC = bTC;
        this.eUR = eUR;
        this.uSD = uSD;
    }

    public Double getBTC() {
        return bTC;
    }

    public void setBTC(Double bTC) {
        this.bTC = bTC;
    }

    public Rates withBTC(Double bTC) {
        this.bTC = bTC;
        return this;
    }

    public Double getEUR() {
        return eUR;
    }

    public void setEUR(Double eUR) {
        this.eUR = eUR;
    }

    public Rates withEUR(Double eUR) {
        this.eUR = eUR;
        return this;
    }

    public Integer getUSD() {
        return uSD;
    }

    public void setUSD(Integer uSD) {
        this.uSD = uSD;
    }

    public Rates withUSD(Integer uSD) {
        this.uSD = uSD;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(bTC);
        dest.writeValue(eUR);
        dest.writeValue(uSD);
    }

    public int describeContents() {
        return 0;
    }

}
