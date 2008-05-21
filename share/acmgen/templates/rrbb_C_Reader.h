using namespace std;

#include <errno.h>
#include <signal.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <unistd.h>

#include <iostream>

#ifndef Reader_h
#define Reader_h

#define ACM_SIZE		%%%ACMgen:ACM_SIZE%%%

#define SHMKEY			((key_t) 21000)
#define SHMKEY1			((key_t) 21100)
#define PERMS			0666

#define acm_t			char

class Reader {
	
	public:
	
		acm_t *shm_data;
		int shm_id;
		pid_t pair_pid;
	
%%%ACMgen:DECLARE_VARS%%%
		
		Reader(pid_t);
		~Reader();
		
		acm_t Read(void);
		acm_t Read1(void);
		acm_t Receive(void);
		void Mu(void);
		
	private:
	
		bool InitLocalShv(void);
		bool InitExternalShv(void);
		void DtLocalShv(void);
		void DtExternalShv(void);
		static void SignalHandler(int);
};
#endif // Reader_h
