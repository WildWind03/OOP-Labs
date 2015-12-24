#pragma once

#include "FilterType.h"
#include "FilterDescription.h"

#include <string>
#include <vector>
#include <stdexcept>
#include <getopt.h>
#include <iostream>

class ConsoleParser
{
	const std::string NO_FILE = "NO_FILE";
	const std::string TOO_SHORT_ARGS_STR = "Incorrect input. Print --help to learn how to use program";
	const std::string emptyFilterListStr = "Error! There isn't any filter!";
	const std::string helpStr = "CIMG THE BEST EDITOR OF BMP24 HELP \n\n"
								"Supported formats: BMP24\n\n"
								"Format of input: inputFilePath outputFilePath -filterName args ... -filterName args\n\n"
								"[--crop width height] - crop filter\n"
								"[--gs] - grayscale filter\n"
								"[--neg] - neagtive filter\n"
								"[--blur sigma] - gaussian-blur filter\n"
								"[--sharp] - sharp filter\n"
								"[--edge threshold] - edge detection filter\n"
								"[--motion angle speed] - motion blur filter (angle - positive integer from 0 to 360, speed - positive integer from 0 to 30\n\n"
								"Example: ./in.bmp ./out.bmp --crop 300 400\n"
								"Example: ./in.bmp ./out.bmp --neg\n"
								"Example: ./in.bmp ./out.bmp --neg --gs --blur 0.5\n";
	const std::string MISSING_ARGUMENT_STR = "Error! Missed argument";
	const std::string UNKNOWN_OPTION_STR = "Error! Unknown option!";

	std::string inputFilePath;
	std::string outputFilePath;

	std::vector <FilterDescription> filtersDescList;


public:
	ConsoleParser(int argc, char *argv[]);

	std::string getInputFilePath() const;
	std::string getOutputFilePath() const;

	std::vector <FilterDescription> getFilterDescriptionList() const;

	ConsoleParser() = delete;
	ConsoleParser (const ConsoleParser & pars) = delete;
	ConsoleParser & operator= (const ConsoleParser & pars) = delete;

	virtual ~ConsoleParser();
};