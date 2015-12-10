#include "Bmp.h"

Bmp::Bmp(std::string inputFileName, std::string outputFileName) : outputFileName(outputFileName)
{
	std::ifstream fin(inputFileName, std::ifstream::binary);

	if (!fin)
	{
		throw std::invalid_argument(NO_FILE_STR);
	}

	read(fin, bmpHeader.bfType, sizeof(bmpHeader.bfType));
	read(fin, bmpHeader.bfSize, sizeof(bmpHeader.bfSize));
	read(fin, bmpHeader.bfReserved1, sizeof(bmpHeader.bfReserved1));
	read(fin, bmpHeader.bfReserved2, sizeof(bmpHeader.bfReserved2));
	read(fin, bmpHeader.bfOffBits, sizeof(bmpHeader.bfOffBits));

	if (bmpHeader.bfType != BMP)
	{
		throw std::invalid_argument(NO_BMP_STR);
	}

	read(fin, bmpHeader.biSize, sizeof(bmpHeader.biSize));

	if (bmpHeader.biSize >= CORE_BMP_SIZE)
	{
		read(fin, bmpHeader.biWidth, sizeof(bmpHeader.biWidth));
		read(fin, bmpHeader.biHeight, sizeof(bmpHeader.biHeight));
		read(fin, bmpHeader.biPlanes, sizeof(bmpHeader.biPlanes));
		read(fin, bmpHeader.biBitCount, sizeof(bmpHeader.biBitCount));

		if (bmpHeader.biBitCount != BMP24)
		{
			throw std::invalid_argument(NO_BMP24_STR);
		}

		height = bmpHeader.biHeight;
		width = bmpHeader.biWidth;

		pixels.resize(bmpHeader.biHeight);

		for (unsigned int i = 0; i < bmpHeader.biHeight; ++i)
		{
			pixels[i].resize(bmpHeader.biWidth);
		}
	}

	if (bmpHeader.biSize >= ADDITIONAL_BMP_SIZE) 
	{
		read(fin, bmpHeader.biCompression, sizeof(bmpHeader.biCompression));
		read(fin, bmpHeader.biSizeImage, sizeof(bmpHeader.biSizeImage));
		read(fin, bmpHeader.biXPelsPerMeter, sizeof(bmpHeader.biXPelsPerMeter));
		read(fin, bmpHeader.biYPelsPerMeter, sizeof(bmpHeader.biYPelsPerMeter));
		read(fin, bmpHeader.biClrUsed, sizeof(bmpHeader.biClrUsed));
		read(fin, bmpHeader.biClrImportant, sizeof(bmpHeader.biClrImportant));
	}

	if (NO_COMPRESSION != bmpHeader.biCompression)
	{
		throw std::invalid_argument(COMPRESSED_FILE_STR);
	}

	size_t linePadding = (BMP_LINE - ((bmpHeader.biWidth * (bmpHeader.biBitCount / COLOR_SIZE)) % BMP_LINE)) & 3;

	unsigned char red;
	unsigned char green;
	unsigned char blue;
 
	for (unsigned int i = 0; i < bmpHeader.biHeight; i++) 
	{
		for (unsigned int j = 0; j < bmpHeader.biWidth; j++)
		{
			read(fin, red, sizeof(red));
			read(fin, green, sizeof(green));
			read(fin, blue, sizeof(blue));

			Pixel * pixel = new Pixel(red, green, blue);

			pixels[i][j] = pixel;
		}

		fin.seekg(linePadding, std::ios_base::cur);
	}
}

size_t Bmp::getHeight() const
{
	return height;
}

size_t Bmp::getWidth() const
{
	return width;
}

void Bmp::save()
{
	std::ofstream fout(outputFileName, std::ifstream::binary);

	if (!fout)
	{
		throw std::invalid_argument(OUTPUT_ERROR_STR);
	}

	write(fout, bmpHeader.bfType);
	write(fout, bmpHeader.bfSize);
	write(fout, bmpHeader.bfReserved1);
	write(fout, bmpHeader.bfReserved2);
	write(fout, bmpHeader.bfOffBits);

	write(fout, bmpHeader.biSize);

	if (bmpHeader.biSize >= CORE_BMP_SIZE)
	{
		write(fout, bmpHeader.biWidth);
		write(fout, bmpHeader.biHeight);
		write(fout, bmpHeader.biPlanes);
		write(fout, bmpHeader.biBitCount);
	}

	if (bmpHeader.biSize >= ADDITIONAL_BMP_SIZE) 
	{
		write(fout, bmpHeader.biCompression);
		write(fout, bmpHeader.biSizeImage);
		write(fout, bmpHeader.biXPelsPerMeter);
		write(fout, bmpHeader.biYPelsPerMeter);
		write(fout, bmpHeader.biClrUsed);
		write(fout, bmpHeader.biClrImportant);
	}

	size_t linePadding = (BMP_LINE - ((bmpHeader.biWidth * (bmpHeader.biBitCount / COLOR_SIZE)) % BMP_LINE)) & 3;
 
	for (unsigned int i = 0; i < bmpHeader.biHeight; i++) 
	{
		for (unsigned int j = 0; j < bmpHeader.biWidth; j++)
		{
			unsigned char red = pixels[i][j] -> getRed();
			unsigned char green = pixels[i][j] -> getGreen();
			unsigned char blue = pixels[i][j] -> getBlue();

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

Pixel * Bmp::getPixel(size_t x, size_t y) 
{
	return pixels[x][y];
}

Bmp::~Bmp()
{
	save();

	for (size_t i = 0; i < pixels.size(); ++i)
	{
		for (size_t k = 0; k < pixels[i].size(); ++k)
		{
			delete pixels[i][k];
		}
	}
}