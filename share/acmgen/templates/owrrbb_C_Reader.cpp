using namespace std;

#include "Reader.h"

extern int errno;

Reader::Reader(pid_t pid) {
	
	pair_pid = pid;
	signal(SIGCONT, SignalHandler);
	
%%%ACMgen:Reader()%%%
	
	InitLocalShv();
	InitExternalShv();
	
	while ((shm_id = shmget(SHMKEY, sizeof(acm_t) * ACM_SIZE * SLOTS, 0)) < 0) {
		
		cerr << "Error creating Reader. Cannot exec shmget()" << endl;
		kill(pair_pid, SIGCONT);
		sleep(1);
//		exit(errno);
	}

	while ((shm_data = (acm_t *) shmat(shm_id, (acm_t *) 0, 0)) == (acm_t *) -1) {
    	
		cerr << "Error creating Reader. Cannot exec shmat()" << endl;
		kill(pair_pid, SIGCONT);
		sleep(1);
//		exit(errno);
	}
}

Reader::~Reader() {

	if (shmdt(shm_data)) {
		
		cout << "Error destroying Reader. Cannont exec shmdt()" << endl;
		exit(errno);
	}

	if (shmctl(shm_id, IPC_RMID, (struct shmid_ds *) 0) < 0) {
		
		cout << "Error destroying Reader. Cannont exec shmctl()" << endl;
		exit(errno);
	}
	
	DtExternalShv();
	DtLocalShv();
}

bool Reader::InitLocalShv(void) {
	
%%%ACMgen:InitLocalShv()%%%
}

bool Reader::InitExternalShv(void) {
	
%%%ACMgen:InitExternalShv()%%%
}

void Reader::DtLocalShv(void) {

%%%ACMgen:DtLocalShv()%%%
}

void Reader::DtExternalShv(void) {

%%%ACMgen:DtExternalShv()%%%
}

acm_t Reader::Read(void) {

	acm_t val = Receive();
	Mu();
	
	return(val);
}

acm_t Reader::Read1(void) {

	Mu();
	acm_t val = Receive();
	
	return(val);
}

acm_t Reader::Receive(void) {
	
	acm_t val;

%%%ACMgen:Receive()%%%

	return(val);
}

void Reader::Mu(void) {
	
	while (true) {
		
%%%ACMgen:Mu()%%%
	}
}

void Reader::SignalHandler(int signo) {

	if (signo == SIGCONT) {}

	return;
}



