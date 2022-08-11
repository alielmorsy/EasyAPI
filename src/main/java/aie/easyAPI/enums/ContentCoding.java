package aie.easyAPI.enums;

/**
 * Enum used only in case of compression in request body. it can be compressed to zip  or GNU zip (zip), or not compressed at all
 */
public enum ContentCoding {
    COMPRESS,
    GZIP,
    NORMAL
}
