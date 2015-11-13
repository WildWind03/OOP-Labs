#pragma once 
#include "GameConf.h"
//#include "GamerFactory.h"

class Controller
{
	class Command
	{
		Controller *ctrl;

	public:

		Command() = delete;

		Command(Controller *ctrl)
		{
			this -> ctrl = ctrl;
		}

		virtual void execute() = 0;

		virtual ~Command(){}

	};

	class GetPlaceForShipCommand : public Command
	{
		size_t pos;

	public:

		GetPlaceForShipCommand () = delete;
		GetPlaceForShipCommand (const GetPlaceForShipCommand &) = delete;
		GetPlaceForShipCommand (Controller *ctrl) : Command(ctrl) {}

		GetPlaceForShipCommand & operator=(const GetPlaceForShipCommand &) = delete;

		virtual void execute() {}

		virtual ~GetPlaceForShipCommand() : ~Command() {}


	};

	GetPlaceForShipCommand *shipCmd;

	GameConf *conf;

	Game *game;

	View *view;

	size_t currentRound;

public:

	Controller(GameConf *conf);

	//void init();

	//void begin();

	~Controller();
};