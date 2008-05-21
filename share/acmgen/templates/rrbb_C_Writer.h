using namespace std;

#include <errno.h>
#include <signal.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <unistd.h>

#include <iostream>

#ifndef Writer_h
#define Writer_h

#define ACM_SIZE		%%%ACMgen:ACM_SIZE%%%

#define SHMKEY			((key_t) 21000)
#define SHMKEY1			((key_t) 21100)
#define PERMS			0666

#define acm_t			char

class Writer {
	
	public:
	
		acm_t *shm_data;
		long long int shm_id;
		pid_t pair_pid;
	
%%%ACMgen:DECLARE_VARS%%%
		
		Writer(pid_t);
		~Writer();
		
		void Write(acm_t);
		void Write1(acm_t);
		void Send(acm_t);
		void Lambda(void);
		
	private:
	
		bool InitLocalShv();
		bool InitExternalShv();
		void DtLocalShv();
		void DtExternalShv();
		static void SignalHandler(int);
};
#endif // Writer_h
