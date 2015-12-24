#include "ConsoleParser.h"

ConsoleParser::ConsoleParser(int argc, char *argv[])
{
    if (argc >= 3)
    {
        inputFilePath = argv[1];
        outputFilePath = argv[2];
    }   
     
	while(true)
	{
	        static struct option long_options[] =
                {
        	        {"crop",  required_argument, 0, 'c'},
        	        {"gs",    no_argument,       0, 'g'},
        	        {"neg",   no_argument,       0, 'n'},
        	        {"blur",  required_argument, 0, 'b'},
        	        {"sharp", no_argument,       0, 's'},
        	        {"edge",  required_argument, 0, 'e'},
        	        {"motion",required_argument, 0, 'm'},
        	        {"help",  no_argument,       0, 'h'},
                    {NULL,    0,                 0,   0},
                }; 

                int option_index = 0;

                int c = getopt_long (argc, argv, "", long_options, &option_index);

                if (-1 == c)
                {
                    if (argc < 3)
                    {
                        throw std::invalid_argument(TOO_SHORT_ARGS_STR);
                    }

                    break;
                }

                switch(c)
                {
                    case 'h' :
                    {
                            std::cout << helpStr << std::endl;
                            exit(0); 
                    }
                    case ':' :
                    {
                            throw std::invalid_argument(MISSING_ARGUMENT_STR);
                    }
                    case '?' :
                    {
                            throw std::invalid_argument(UNKNOWN_OPTION_STR);
                    }
                }

                if (nullptr == optarg)
                {
                    FilterDescription filterDescription(c);
                    filtersDescList.push_back(filterDescription);
                }
                else
                {

                    FilterDescription filterDescription(c);
                    filterDescription.addParameter(optarg);

                    for (size_t i = optind; i < argc; ++i)
                    {
                        if (argv[i][0] != '-')
                        {
                                filterDescription.addParameter(argv[i]);
                        }
                        else
                        {
                                break;
                        }
                    }
                    
                    filtersDescList.push_back(filterDescription);   
                }
	}	
}

std::string ConsoleParser::getInputFilePath() const
{
	return inputFilePath;
}

std::string ConsoleParser::getOutputFilePath() const
{
	return outputFilePath;
}

std::vector <FilterDescription> ConsoleParser::getFilterDescriptionList() const
{
	return filtersDescList;
}

ConsoleParser::~ConsoleParser()
{

}