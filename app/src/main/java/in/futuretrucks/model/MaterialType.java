package in.futuretrucks.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MaterialType {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("material_type")
    @Expose
    private String materialType;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * No args constructor for use in serialization
     *
     */
    public MaterialType() {
    }

    /**
     *
     * @param materialType
     * @param Id
     * @param code
     */
    public MaterialType(String Id, String materialType, String code) {
        this.Id = Id;
        this.materialType = materialType;
        this.code = code;
    }

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The materialType
     */
    public String getMaterialType() {
        return materialType;
    }

    /**
     *
     * @param materialType
     * The material_type
     */
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MaterialType{" +
                "Id='" + Id + '\'' +
                ", materialType='" + materialType + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}