#pragma once

struct BmpInfoHeader
{
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