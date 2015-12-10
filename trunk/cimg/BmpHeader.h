#pragma once

struct BmpHeader
{
    unsigned short bfType;

    unsigned int bfSize;
    unsigned short bfReserved1;
    unsigned short bfReserved2;
    unsigned int bfOffBits;

    unsigned int biSize;
    unsigned int biWidth;
    unsigned int biHeight;

    unsigned short biPlanes;
    unsigned short biBitCount;

    unsigned int biCompression;
    unsigned int biSizeImage;
    unsigned int biXPelsPerMeter;
    unsigned int biYPelsPerMeter;
    unsigned int biClrUsed;
    unsigned int biClrImportant;
};