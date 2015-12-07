#include "Controller.h"

Controller::Controller (GameConf  & conf)
{
	countOfRounds = conf.getCountRound();

	gamer1 = GamerFactory::CreateGamer(conf.getFPlayer());
	gamer2 = GamerFactory::CreateGamer(conf.getSPlayer());

	stat = new Statistics();

	game = new Game(*gamer1, *gamer2);
}

void Controller::beginGame()
{
	for (size_t i = 0; i < countOfRounds; ++i)
	{
		GamerNum winnerGamerNum;

		try
		{
			winnerGamerNum = game -> newGame();
		}
		catch(const GameExitException & exitException)
		{
			return;
		}


		if (GamerNum::Gamer1 == winnerGamerNum)
		{
			stat -> addG1Vic();
		}
		else
		{
			stat -> addG2Vic();
		}
	}

	gamer1 -> onGetStatistics(*stat);
	gamer2 -> onGetStatistics(*stat);

	ConsoleView view; //just to see statistics when random and optimal gamers play
	view.printStatistics(*stat);
}

Controller::~Controller()
{
	delete(stat);
	delete(game);
	delete(gamer1);
	delete(gamer2);
}