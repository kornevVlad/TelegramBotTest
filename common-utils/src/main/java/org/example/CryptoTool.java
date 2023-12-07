package org.example;


import org.hashids.Hashids;

/**
 * Хэширование данных для БД(скрытие исходного HTTP)
 */
public class CryptoTool {

    private final Hashids hashids;

    public CryptoTool(String salt) {
        var minHashLength = 10; //длина передаваемого хэша
        this.hashids = new Hashids(salt, minHashLength);
    }

    /**
     * шифрование в хэш
     */
    public String hashOf(Long value) {
        return hashids.encode(value);
    }

    /**
     * дешифрование хэша обратно в число
     */

    public Long idOf(String value) {
        long[] res = hashids.decode(value);
        if (res != null && res.length > 0) {
            return res[0];
        }
        return null;
    }
}
