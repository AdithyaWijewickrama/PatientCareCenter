package com.pcc.PatientCareCenter.Model;

import java.util.ArrayList;
import java.util.List;

public enum MedicineType {
    TABLET("Tablet"),
    CAPSULE("Capsule"),
    SYRUP("Syrup"),
    LOTION("Lotion"),
    CREAM("Cream"),
    OINTMENT("Ointment"),
    GEL("Gel"),
    INHALER("Inhaler"),
    NASAL_SPRAY("Nasal Spray"),
    EYE_DROPS("Eye Drops"),
    EAR_DROPS("Ear Drops"),
    INJECTION("Injection"),
    SUPPOSITORY("Suppository"),
    PATCH("Patch"),
    POWDER("Powder"),
    SOLUTION("Solution"),
    SUSPENSION("Suspension"),
    EMULSION("Emulsion"),
    SPRAY("Spray"),
    FOAM("Foam"),
    SHAMPOO("Shampoo"),
    SOAP("Soap"),
    LOZENGES("Lozenges"),
    CHEWABLE_TABLET("Chewable Tablet"),
    EFFERVESCENT_TABLET("Effervescent Tablet"),
    IMPLANT("Implant"),
    PESSARY("Pessary"),
    ENEMA("Enema"),
    INHALATION_POWDER("Inhalation Powder"),
    INHALATION_SOLUTION("Inhalation Solution"),
    TRANSDERMAL_PATCH("Transdermal Patch"),
    SUBLINGUAL_TABLET("Sublingual Tablet"),
    BUCCAL_TABLET("Buccal Tablet"),
    GRANULES("Granules"),
    PELLETS("Pellets"),
    PASTE("Paste"),
    TINCTURE("Tincture"),
    ELIXIR("Elixir"),
    LINIMENT("Liniment"),
    COLLODION("Collodion"),
    AEROSOL("Aerosol"),
    INHALATION_AEROSOL("Inhalation Aerosol"),
    INHALATION_SPRAY("Inhalation Spray"),
    INHALATION_SUSPENSION("Inhalation Suspension"),
    INHALATION_GAS("Inhalation Gas"),
    INHALATION_VAPOR("Inhalation Vapor"),
    INHALATION_NEBULIZER("Inhalation Nebulizer"),
    INHALATION_DRY_POWDER("Inhalation Dry Powder"),
    INHALATION_METERED_DOSE("Inhalation Metered Dose"),
    INHALATION_SOFT_MIST("Inhalation Soft Mist"),
    INHALATION_BREATH_ACTUATED("Inhalation Breath-Actuated"),
    INHALATION_PRESSURIZED("Inhalation Pressurized"),
    INHALATION_NON_PRESSURIZED("Inhalation Non-Pressurized"),
    INHALATION_SINGLE_DOSE("Inhalation Single-Dose"),
    INHALATION_MULTI_DOSE("Inhalation Multi-Dose"),
    INHALATION_DISPOSABLE("Inhalation Disposable"),
    INHALATION_REUSABLE("Inhalation Reusable"),
    INHALATION_CARTRIDGE("Inhalation Cartridge"),
    INHALATION_CAPSULE("Inhalation Capsule"),
    INHALATION_DISKUS("Inhalation Diskus"),
    INHALATION_TURBUHALER("Inhalation Turbuhaler"),
    INHALATION_AUTOHALER("Inhalation Autohaler"),
    INHALATION_FLEXHALER("Inhalation Flexhaler"),
    INHALATION_TWISTHALER("Inhalation Twisthaler"),
    INHALATION_RESPIMAT("Inhalation Respimat"),
    INHALATION_ELLIPTA("Inhalation Ellipta"),
    INHALATION_GENUAIR("Inhalation Genuair"),
    INHALATION_BREEZHALER("Inhalation Breezhaler"),
    INHALATION_HANDIHALER("Inhalation Handihaler"),
    INHALATION_NEOHALER("Inhalation Neohaler"),
    INHALATION_PODHALER("Inhalation Podhaler"),
    INHALATION_ZONDA("Inhalation Zonda"),
    INHALATION_ZONDA_XL("Inhalation Zonda XL"),
    INHALATION_ZONDA_XS("Inhalation Zonda XS"),
    INHALATION_ZONDA_S("Inhalation Zonda S"),
    INHALATION_ZONDA_M("Inhalation Zonda M"),
    INHALATION_ZONDA_L("Inhalation Zonda L"),
    INHALATION_ZONDA_XXL("Inhalation Zonda XXL");

    private final String displayName;

    MedicineType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static List<String> getList(){
        List<String> list=new ArrayList<>();
        for(MedicineType type:values()){
            list.add(type.displayName);
        }
        return list;
    }
    public static MedicineType fromDisplayName(String displayName) {
        for (MedicineType type : MedicineType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name: " + displayName);
    }
}