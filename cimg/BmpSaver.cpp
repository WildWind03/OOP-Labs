#include "BmpSaver.h"

#include <stdexcept>

BmpSaver::BmpSaver()
{

}

BmpFileHeader BmpSaver::generateBmpFileHeader(const Image & image)
{
	BmpFileHeader bmpFileHeader;

	bmpFileHeader.bfType = BMP_TYPE;

	size_t pad = BMP_LINE - ((image.getWidth() * BYTES_FOR_PIXELS) % BMP_LINE);

	size_t extraBytes = pad * image.getHeight();

	bmpFileHeader.bfSize = sizeof(BmpFileHeader) + sizeof(BmpInfoHeader) + image.getSize() + extraBytes;
	bmpFileHeader.bfReserved1 = 0;
	bmpFileHeader.bfReserved2 = 0;
	bmpFileHeader.bfOffBits = bitOff;

	return bmpFileHeader;
}

BmpInfoHeader BmpSaver::generateBmpInfoHeader(const Image & image)
{
	BmpInfoHeader bmpInfoHeader;

	bmpInfoHeader.biSize = sizeof(BmpInfoHeader);
	bmpInfoHeader.biWidth = image.getWidth();
	bmpInfoHeader.biHeight = image.getHeight();
	bmpInfoHeader.biPlanes = 1;
	bmpInfoHeader.biBitCount = BIT_COUNT;
	bmpInfoHeader.biCompression = 0;
	bmpInfoHeader.biSizeImage = 0;
	bmpInfoHeader.biXPelsPerMeter = 0;
	bmpInfoHeader.biYPelsPerMeter = 0;
    bmpInfoHeader.biClrUsed = 0;
    bmpInfoHeader.biClrImportant = 0;

    return bmpInfoHeader;
}

void BmpSaver::save(std::string filePath, const Image & image)
{
	std::ofstream fout(filePath, std::ifstream::binary);

	if (!fout)
	{
		throw std::invalid_argument(OUTPUT_ERROR_STR);
	}
	
	BmpFileHeader bmpFileHeader = generateBmpFileHeader(image);
	BmpInfoHeader bmpInfoHeader = generateBmpInfoHeader(image);

	write(fout, bmpFileHeader.bfType);
	write(fout, bmpFileHeader.bfSize);
	write(fout, bmpFileHeader.bfReserved1);
	write(fout, bmpFileHeader.bfReserved2);
	write(fout, bmpFileHeader.bfOffBits);

	write(fout, bmpInfoHeader.biSize);

	write(fout, bmpInfoHeader.biWidth);
	write(fout, bmpInfoHeader.biHeight);
	write(fout, bmpInfoHeader.biPlanes);
	write(fout, bmpInfoHeader.biBitCount);
	write(fout, bmpInfoHeader.biCompression);
	write(fout, bmpInfoHeader.biSizeImage);
	write(fout, bmpInfoHeader.biXPelsPerMeter);
	write(fout, bmpInfoHeader.biYPelsPerMeter);
	write(fout, bmpInfoHeader.biClrUsed);
	write(fout, bmpInfoHeader.biClrImportant);

	size_t linePadding = 0;

	if ((bmpInfoHeader.biWidth * (bmpInfoHeader.biBitCount / COLOR_SIZE)) % BMP_LINE != 0)
	{
		 linePadding = BMP_LINE - ((bmpInfoHeader.biWidth * (bmpInfoHeader.biBitCount / COLOR_SIZE)) % BMP_LINE);
	}

	for (unsigned int i = 0; i < bmpInfoHeader.biHeight; i++)
	{
		for (unsigned int j = 0; j < bmpInfoHeader.biWidth; j++)
		{
            Pixel currentPixel = image.getPixel(j, bmpInfoHeader.biHeight - i - 1);

			unsigned char red = currentPixel.getRed();
			unsigned char green = currentPixel.getGreen();
			unsigned char blue = currentPixel.getBlue();

			write(fout, red);
			write(fout, green);
			write(fout, blue);
		}

		for (size_t i = 0; i < linePadding; ++i)
		{
			unsigned char rubbish = RUBBISH_CHAR;
			write(fout, rubbish);
		}
	}
}

BmpSaver::~BmpSaver()
{

}
