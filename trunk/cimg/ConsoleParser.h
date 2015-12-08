#pragma once

#include "FilterType.h"
#include "FilterDescryption.h"

#include <string>
#include <list>
#include <stdexcept>
#include <getopt.h>
#include <iostream>

class ConsoleParser
{
	void parse();

	const std::string emptyFilterListStr = "Error! There isn't any filter!";
	const std::string helpStr = "HELP";

	std::string inputFilePath;
	std::string outputFilePath;

	std::list <FilterDescryption> filtersDescList;


public:
	ConsoleParser(int argc, char *argv[]);

	std::string getInputFilePath() const;
	std::string getOutputFilePath() const;

	FilterDescryption getNextFilterDescryption();

	ConsoleParser() = delete;
	ConsoleParser (const ConsoleParser & pars) = delete;
	ConsoleParser & operator= (const ConsoleParser & pars) = delete;

	~ConsoleParser();
};