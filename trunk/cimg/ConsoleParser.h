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
	void parse();

	const std::string emptyFilterListStr = "Error! There isn't any filter!";
	const std::string helpStr = "HELP";

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

	~ConsoleParser();
};