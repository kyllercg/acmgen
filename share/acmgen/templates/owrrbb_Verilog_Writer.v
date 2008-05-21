// The Writer process module
module writer_mod(we, l, re, rne, ra, req, ack, add, rdack, smreq, smack);

	// port declarations
	input re;
	input rne;
	input ra;
	input req;
	input rdack;
	input smack;
	output we;
	output l;
	output ack;
	output add;
	output smreq;

	// input ports
	wire [0:%%%ACMgen:ACM_SIZE*2%%%] re;
	wire [0:%%%ACMgen:ACM_SIZE*2%%%] rne;
	wire [0:%%%ACMgen:ACM_SIZE%%%] ra;
	wire req;
	wire rdack;
	wire smack;

	// output ports
	reg [0:%%%ACMgen:ACM_SIZE%%%] we;
	reg [0:%%%ACMgen:ACM_SIZE*2%%%] l;
	reg ack;
	integer add;
	reg smreq;
	
	// internal variables
	reg wi [0:%%%ACMgen:ACM_SIZE*2%%%];
	reg pwi [0:%%%ACMgen:ACM_SIZE*2%%%];

	initial begin
		%%%ACMgen:InitExternal()%%%
		
		%%%ACMgen:InitLocal()%%%
		
		ack = 0;
		smreq = 0;
		// add = 0;
	end
	
	//always @(posedge (req || rdack))
	always @(posedge req)
	begin
		%%%ACMgen:Lambda()%%%
		
		%%%ACMgen:Send()%%%
	end

	always @(smack)
	begin
		ack = 0;
		ack = 1;
	end

endmodule // end of writer_mod module
