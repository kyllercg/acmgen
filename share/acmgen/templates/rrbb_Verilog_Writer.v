// The Writer process module
module writer_mod(wee, wnee, ree, rneein, req, ack, add, smreq, smack, wsel, wreq, wack, rsel, rreq, rackin, clock);

// port declarations
input ree;
input rneein;
input req;
input smack;
input rsel;
input rreq;
input rackin;
input clock;
output wee;
output wnee;
output ack;
output add;
output smreq;
output wsel;
output wreq;
output wack;

// input ports
wire ree;
wire rneein;
wire req;
wire smack;
wire [31:0] rsel;
wire rreq;
wire rackin;
wire clock;

// output ports
reg wee;
reg wnee;
reg ack;
integer add;
reg smreq;
integer wsel;
reg wreq;
reg wack;

// internal variables
reg we[0:%%%ACMgen:ACM_SIZE%%%];
reg wne [0:%%%ACMgen:ACM_SIZE%%%];
reg wi [0:%%%ACMgen:ACM_SIZE%%%];
reg pwi [0:%%%ACMgen:ACM_SIZE%%%];

reg rack1;
reg rack;
reg rnee1;
reg rnee;

initial begin
	// "external" variables
	%%%ACMgen:InitExternal()%%%
	
	// local variables
	%%%ACMgen:InitLocal()%%%
	
	wee = 1;
	wnee = 0;
	wsel = 2;
	wreq = 1;
	wack = 0;
	ack = 0;
	smreq = 0;
	rack1 = rackin;
	rack = rackin;
	rnee1 = rneein;
	rnee = rneein;
	// add = 0;
end

always @(posedge clock)
begin
	if (req == 1 && smreq == 0 && wreq == 0)
	begin
		wreq = 1;
	end else if (req == 1 && smreq == 0 && rack == 1)
	begin
		// here goes the control actions (\lambda)
		%%%ACMgen:Lambda()%%%
		
		// here goes the memory access actions
		%%%ACMgen:Send()%%%
	end else if (req == 0)
	begin
		smreq = 0;
		ack = 0;
		wreq = 0;
	end
end

// get ack from shared memory module and pass to the writer process
always @(posedge clock)
begin
	if (smreq == 1 && smack == 1)
	begin
		ack = 1;
	end
end

// this is the multiplexer
always @(posedge clock)
begin
	if (rreq == 1 && wack == 0)
	begin
		assign wee = we[rsel];
		assign wnee = wne[rsel];
		wack = 1;
	end else if (rreq == 0)
	begin
		wack = 0;
	end
end

always @(posedge clock)
begin
	rnee1 = rneein;
	rack1 = rackin;
end

always @(posedge clock)
begin
	rnee = rnee1;
	rack = rack1;
end

endmodule // end of writer_mod module
