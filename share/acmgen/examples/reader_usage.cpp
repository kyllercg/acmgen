/**
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * $Id$
 */

using namespace std;

#include "Reader.h"

static void sigusr(int signo) {

	if (signo == SIGCONT) {}

	return;
}


int main(void) {

	char data;
	int id = 0;

	cout << "pid: " << getpid() << endl;
	
	cout << "digite id do writer: ";
	cin >> id;

	Reader *rd = new Reader((pid_t)id);

	do {

		data = rd->Read();
		cout << data << endl;
		sleep(1);
	} while (data != '.');

	cout << endl;

	rd->~Reader();
}
