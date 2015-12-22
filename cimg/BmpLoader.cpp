#include "BmpLoader.h"

BmpLoader::BmpLoader()
{

}

Image BmpLoader::load(std::string filePath)
{
	BmpFileHeader bmpFileHeader;
	BmpInfoHeader bmpInfoHeader;

	std::vector<std::vector<Pixel>> pixels;

	std::ifstream fin(filePath, std::ifstream::binary);

	if (!fin)
	{
		throw std::invalid_argument(NO_FILE_STR);
	}

	read(fin, bmpFileHeader.bfType, sizeof(bmpFileHeader.bfType));

	if (bmpFileHeader.bfType != BMP)
	{
		throw std::invalid_argument(NO_BMP_STR);
	}

	read(fin, bmpFileHeader.bfSize, sizeof(bmpFileHeader.bfSize));
	read(fin, bmpFileHeader.bfReserved1, sizeof(bmpFileHeader.bfReserved1));
	read(fin, bmpFileHeader.bfReserved2, sizeof(bmpFileHeader.bfReserved2));
	read(fin, bmpFileHeader.bfOffBits, sizeof(bmpFileHeader.bfOffBits));

	read(fin, bmpInfoHeader.biSize, sizeof(bmpInfoHeader.biSize));

	if (bmpInfoHeader.biSize >= CORE_BMP_SIZE)
	{
		read(fin, bmpInfoHeader.biWidth, sizeof(bmpInfoHeader.biWidth));
		read(fin, bmpInfoHeader.biHeight, sizeof(bmpInfoHeader.biHeight));
		read(fin, bmpInfoHeader.biPlanes, sizeof(bmpInfoHeader.biPlanes));
		read(fin, bmpInfoHeader.biBitCount, sizeof(bmpInfoHeader.biBitCount));

		if (bmpInfoHeader.biBitCount != BMP24)
		{
			throw std::invalid_argument(NO_BMP24_STR);
		}

		pixels.resize(bmpInfoHeader.biWidth);

		for (unsigned int i = 0; i < bmpInfoHeader.biWidth; ++i)
		{
			pixels[i].resize(bmpInfoHeader.biHeight);
		}
	}
	else
	{
		throw std::invalid_argument(NO_BMP24_STR);
	}

	if (bmpInfoHeader.biSize >= ADDITIONAL_BMP_SIZE)
	{
		read(fin, bmpInfoHeader.biCompression, sizeof(bmpInfoHeader.biCompression));
		read(fin, bmpInfoHeader.biSizeImage, sizeof(bmpInfoHeader.biSizeImage));
		read(fin, bmpInfoHeader.biXPelsPerMeter, sizeof(bmpInfoHeader.biXPelsPerMeter));
		read(fin, bmpInfoHeader.biYPelsPerMeter, sizeof(bmpInfoHeader.biYPelsPerMeter));
		read(fin, bmpInfoHeader.biClrUsed, sizeof(bmpInfoHeader.biClrUsed));
		read(fin, bmpInfoHeader.biClrImportant, sizeof(bmpInfoHeader.biClrImportant));
	}

	if (NO_COMPRESSION != bmpInfoHeader.biCompression)
	{
		throw std::invalid_argument(COMPRESSED_FILE_STR);
	}

	fin.seekg(bmpFileHeader.bfOffBits, std::ios_base::beg);

	size_t linePadding = 0;
	
	if ((bmpInfoHeader.biWidth * (bmpInfoHeader.biBitCount / COLOR_SIZE)) % BMP_LINE != 0)
	{
		 linePadding = BMP_LINE - ((bmpInfoHeader.biWidth * (bmpInfoHeader.biBitCount / COLOR_SIZE)) % BMP_LINE);
	}

	unsigned char red;
	unsigned char green;
	unsigned char blue;

	for (unsigned int i = 0; i < bmpInfoHeader.biHeight; i++)
	{
		for (unsigned int j = 0; j < bmpInfoHeader.biWidth; j++)
		{
			read(fin, red, sizeof(red));
			read(fin, green, sizeof(green));
			read(fin, blue, sizeof(blue));

			pixels[j][bmpInfoHeader.biHeight - i - 1] = Pixel(red, green, blue);
		}

		fin.seekg(linePadding, std::ios_base::cur);
	}

	Image image(pixels);

	return image;
}

BmpLoader::~BmpLoader()
{

}
