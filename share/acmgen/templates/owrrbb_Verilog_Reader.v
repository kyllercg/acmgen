// The Reader process module
module reader_mod(re, rne, ra, we, l, req, ack, add, smreq, smack, rrun);

	// port declarations
	input we;
	input l;
	input req;
	input smack;
	output re;
	output rne;
	output ra;
	output ack;
	output add;
	output smreq;
	output rrun;

	// input ports
	wire [0:%%%ACMgen:ACM_SIZE%%%] we;
	wire [0:%%%ACMgen:ACM_SIZE*2%%%] l;
	wire req;
	wire smack;

	// output ports
	reg [0:%%%ACMgen:ACM_SIZE*2%%%] re;
	reg [0:%%%ACMgen:ACM_SIZE*2%%%] rne;
	reg [0:%%%ACMgen:ACM_SIZE%%%] ra;
	reg ack;
	integer add;
	reg smreq;
	reg rrun;

	// internal variables
	reg ri [0:%%%ACMgen:ACM_SIZE*2%%%];
	reg pri [0:%%%ACMgen:ACM_SIZE*2%%%];

	integer rd_data;

	initial begin
		%%%ACMgen:InitExternal()%%%
		
		%%%ACMgen:InitLocal()%%%

		ack = 0;
		smreq = 0;
		rrun = 0;
		// add = 2;
	end

	always @(posedge req)
	begin
		%%%ACMgen:Mu()%%%
		
		%%%ACMgen:Receive()%%%
	end
	
	always #1
	begin
		%%%ACMgen:RunBabyRun()%%%
	end

	always @(smack)
	begin
		ack = 0;
		ack = 1;
	end

endmodule // end of reader_mod module
