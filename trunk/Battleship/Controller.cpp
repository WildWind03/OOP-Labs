#include "Controller.h"

Controller::Controller (GameConf  & conf)
{
	countOfRounds = conf.getCountRound();

	g1 = GamerFactory::CreateGamer(conf.getFPlayer());
	g2 = GamerFactory::CreateGamer(conf.getSPlayer());

	stat = new Statistics();

	game = new Game(*g1, *g2);
}

void Controller::beginGame()
{
	for (size_t i = 0; i < countOfRounds; ++i)
	{
		GamerNum winner;

		try
		{
			winner = game -> newGame();
		}
		catch(const GameExitEvent & exitStr)
		{
			return;
		}


		if (GamerNum::Gamer1 == winner)
		{
			stat -> addG1Vic();
		}
		else
		{
			stat -> addG2Vic();
		}
	}

	g1 -> onGetStatistics(*stat);
	g2 -> onGetStatistics(*stat);

	ConsoleView view; //just to see statistics when random and optimal gamers play
	view.printStatistics(*stat);
}

Controller::~Controller()
{
	delete(stat);
	delete(game);
	delete(g1);
	delete(g2);
}