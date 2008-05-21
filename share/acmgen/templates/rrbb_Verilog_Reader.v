// The Reader process module
module reader_mod(ree, rnee, weein, wneein, req, ack, add, smreq, smack, rsel, rreq, rack, wsel, wreq, wackin, clock);

// port declarations
input weein;
input wneein;
input req;
input smack;
input wsel;
input wreq;
input wackin;
input clock;
output ree;
output rnee;
output ack;
output add;
output smreq;
output rsel;
output rreq;
output rack;

// input ports
wire weein;
wire wneein;
wire req;
wire smack;
wire [31:0] wsel;
wire wreq;
wire wackin;
wire clock;

// output ports
reg ree;
reg rnee;
reg ack;
integer add;
reg smreq;
integer rsel;
reg rreq;
reg rack;

// internal variables
reg re[0:%%%ACMgen:ACM_SIZE%%%];
reg rne [0:%%%ACMgen:ACM_SIZE%%%];
reg ri [0:%%%ACMgen:ACM_SIZE%%%];
reg pri [0:%%%ACMgen:ACM_SIZE%%%];

reg wack1;
reg wack;
reg wee1;
reg wee;
reg wnee1;
reg wnee;

integer rd_data;

initial begin
	// "external" variables
	%%%ACMgen:InitExternal()%%%
	
	// local variables
	%%%ACMgen:InitLocal()%%%
	
	ree = 0;
	rnee = 1;
	rsel = 1;
	rreq = 1;
	rack = 0;
	ack = 0;
	smreq = 0;
	wack1 = wackin;
	wack = wackin;
	wee1 = weein;
	wee = weein;
	wnee1 = wneein;
	wnee = wneein;
	// add = 2;
end

always @(posedge clock)
begin
	if (req == 1 && smreq == 0 && rreq == 0)
	begin
		rreq = 1;
	end else if (req == 1 && smreq == 0 && wack == 1)
	begin
		/// here goes the control actions (\mu)
		%%%ACMgen:Mu()%%%
		
		// here goes the memory access actions
		%%%ACMgen:Receive()%%%
	end else if (req == 0)
	begin
		smreq = 0;
		ack = 0;
		rreq = 0;
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
	if (wreq == 1 && rack == 0)
	begin
		assign ree = re[wsel];
		assign rnee = rne[wsel];
		rack = 1;
	end else if (wreq == 0)
	begin
		rack = 0;
	end
end

always @(posedge clock)
begin
	wee1 = weein;
	wnee1 = wneein;
	wack1 = wackin;
end

always @(posedge clock)
begin
	wee = wee1;
	wnee = wnee1;
	wack = wack1;
end

endmodule // end of reader_mod module
