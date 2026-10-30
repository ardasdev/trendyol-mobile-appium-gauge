package com.example.steps;

import com.example.base.Driver;
import com.example.base.ElementHelper;
import com.thoughtworks.gauge.Step;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonSteps {

    private ElementHelper helper() {
        return new ElementHelper(Driver.get());
    }

    @Step("<key> elementine tıkla")
    public void tikla(String key) {
        helper().click(key);
    }

    @Step("<key> elementine <deger> yaz")
    public void yaz(String key, String deger) {
        helper().type(key, deger);
    }

    @Step("<key> elementine <degisken> ortam değişkeni yazılır")
    public void ortamDegiskeniYaz(String key, String degisken) {
        helper().type(key, ortamDegiskeni(degisken));
    }

    private static String ortamDegiskeni(String ad) {
        String deger = System.getenv(ad);
        if (deger == null || deger.isBlank()) {
            throw new IllegalStateException("'" + ad + "' ortam değişkeni tanımlı değil");
        }
        return deger;
    }

    @Step("<key> elementi görünür olur")
    public void gorunurOlur(String key) {
        assertTrue(helper().isVisible(key), "'" + key + "' görünür olmalı");
    }

    @Step("<key> elementi <beklenen> metnini içerir")
    public void metinIcerir(String key, String beklenen) {
        assertTrue(helper().textContains(key, beklenen),
                "'" + key + "' metni '" + beklenen + "' içermeli");
    }
    @Step("<key> elementi seçili olur")
    public void seciliOlur(String key) {
        assertTrue(helper().isChecked(key),
                "'" + key + "' seçili (checked=true) olmalı");
    }

    @Step("<key> elementi <deger> ile <deger2> TL aralığındaki ürünleri içerir")
    public void fiyatAralik(String key, String deger, String deger2) {
        ElementHelper h = helper();
        assertTrue(h.textContains(key, deger) && h.textContains(key, deger2),
                "'" + key + "' metni '" + deger + "' ve '" + deger2 + "' değerlerini içermeli");
    }

  @Step("Gerekliyse giriş yapılır")
    public void gerekliyseGirisYap() {
        ElementHelper h = helper();
        h.click("hesabim_tab");
        if (h.isPresent("giris_yap_uye_ol_butonu", 5)) {
            h.click("giris_yap_uye_ol_butonu");
            h.click("eposta_input");
            h.type("eposta_input", ortamDegiskeni("TRENDYOL_EMAIL"));
            h.click("devam_et_butonu");
            h.click("sifre_input");
            h.type("sifre_input", ortamDegiskeni("TRENDYOL_SIFRE"));
            h.click("giris_yap_butonu");
        }
        assertTrue(h.isVisible("hesabim_kullanici_adi"),
                "Giriş doğrulanamadı: 'hesabim_kullanici_adi' görünür olmalı");
    }

    @Step("<paket> paketi açılır")
    public void paketAcilir(String paket) {
        assertTrue(helper().isPackageOpened(paket), paket + " paketi açılmadı.");
    }

    @Step("Varsa <key> popup kapatılır")
    public void varsaPopupKapat(String key) {
        helper().varsaKapat(key);
    }

    @Step("Ana ekrana dön")
    public void anaEkranaDon() {
        helper().pressKey(3);
    }

    @Step("Enter tuşuna basılır")
    public void enterTusunaBas()  {
        helper().pressKey(66);
    }

    @Step("<key> elementine kadar kaydır")
    public void elementeKadarKaydir(String key) {
        assertTrue(helper().scrollToElement(key, 5),
                "'" + key + "' 5 kaydırma sonrasında da bulunamadı");
    }
}
