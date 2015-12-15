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
	const std::string TOO_SHORT_ARGS_STR = "Incorrect input. Print --help to learn how to use program";
	const std::string emptyFilterListStr = "Error! There isn't any filter!";
	const std::string helpStr = "CIMG THE BEST EDITOR OF BMP24 HELP \n\n"
								"Supported formats: BMP24\n\n"
								"Format of input: inputFilePath outputFilePath -filterName args ... -filterName args\n\n"
								"[--crop(-c) width height] - crop filter\n"
								"[--gs(-g)] - grayscale filter\n"
								"[--neg(-n)] - neagtive filter\n"
								"[--blur(-b) sigma] - gaussian-blur filter\n"
								"[--sharp(-s)] - sharp filter\n"
								"[--edge(-e) threshold] - edge detection filter\n"
								"[--motion(-m)] - motion blur filter\n\n"
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

	std::vector <FilterDescription> getFilterDescriptionList();

	ConsoleParser() = delete;
	ConsoleParser (const ConsoleParser & pars) = delete;
	ConsoleParser & operator= (const ConsoleParser & pars) = delete;

	virtual ~ConsoleParser();
};