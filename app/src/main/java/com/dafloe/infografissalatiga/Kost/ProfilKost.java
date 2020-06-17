package com.dafloe.infografissalatiga.Kost;

public class ProfilKost {
    private String mImageResourceKost;
    private String mTextKostJudul;
    private String mTextKostAlamat;

    public ProfilKost(String imageResourceKost, String textKostJudul, String textKostAlamat){
        mImageResourceKost = imageResourceKost;
        mTextKostJudul = textKostJudul;
        mTextKostAlamat = textKostAlamat;
    }

    public String getmImageResourceKost(){
        return mImageResourceKost;
    }
    public String getmTextKostJudul(){
        return mTextKostJudul;
    }
    public String getmTextKostAlamat(){
        return mTextKostAlamat;
    }
}
