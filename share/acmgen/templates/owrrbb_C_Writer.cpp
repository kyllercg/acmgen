using namespace std;

#include "Writer.h"

extern int errno;

Writer::Writer(pid_t pid) {
	
	pair_pid = pid;
	signal(SIGCONT, SignalHandler);
	
	if ((shm_id = shmget(SHMKEY, sizeof(acm_t) * ACM_SIZE * SLOTS, PERMS | IPC_CREAT)) < 0) {
		
		cerr << "Error creating Writer. Cannot exec shmget()" << endl;
		exit(errno);
	}

	if ((shm_data = (acm_t *) shmat(shm_id, (acm_t *) 0, 0)) == (acm_t *) -1) {
		
		cerr << "Error creating Writer. Cannot exec shmat()" << endl;
		exit(errno);
	}
	
	*shm_data = '-';
	
%%%ACMgen:Writer()%%%
	
	InitLocalShv();
	InitExternalShv();
}

Writer::~Writer() {

	if (shmdt(shm_data)) {
		
		cout << "Error destroying Writer. Cannont exec shmdt()" << endl;
		exit(errno);
	}
	
	DtExternalShv();
	DtLocalShv();
}

bool Writer::InitLocalShv(void) {
	
%%%ACMgen:InitLocalShv()%%%
}

bool Writer::InitExternalShv(void) {
	
%%%ACMgen:InitExternalShv()%%%
}

void Writer::DtLocalShv(void) {

%%%ACMgen:DtLocalShv()%%%
}

void Writer::DtExternalShv(void) {

%%%ACMgen:DtExternalShv()%%%
}

void Writer::Write(acm_t val) {

	Send(val);
	Lambda();
}

void Writer::Write1(acm_t val) {

	Lambda();
	Send(val);
}

void Writer::Send(acm_t val) {

%%%ACMgen:Send()%%%
}

void Writer::Lambda(void) {
		
%%%ACMgen:Lambda()%%%
}

void Writer::SignalHandler(int signo) {

	if (signo == SIGCONT) {}

	return;
}



