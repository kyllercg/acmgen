/*
 * Copyright (C) 2006, 2008 - Kyller Costa Gorgônio
 * Copyright (C) 2006, 2008 - Universitat Politècnica de Catalunya
 *
 * See LGPL for further details.
 */

module shmem(
	wreq,
	rreq,
	reset,
	clock_rd,
	clock_wr,
	raddr,
	waddr,
	wdata,
	wack,
	rack,
	rdata
);

parameter
	IDLE      = 0,
	ACCESSING = 1;

input	wreq;
input	rreq;
input	reset;
input	clock_rd;
input	clock_wr;
input	[%%%ACMgen:ACM_SIZE_LOG%%%:0] raddr;
input	[%%%ACMgen:ACM_SIZE_LOG%%%:0] waddr;
input	[31:0] wdata;
output	wack;
output	rack;
output	[31:0] rdata;

reg	wack;
reg	rack;
reg [31:0] rdata;

reg [31:0] ACM [%%%ACMgen:ACM_SIZE%%%:0];
reg [%%%ACMgen:ACM_SIZE_LOG%%%:0] state_wr, state_rd, next_wr, next_rd;

always @(wreq or reset) begin
	if (reset) begin
		wack <= 1'b0;
		ACM[0] <= wdata;
		next_wr <= 2'b0;
		next_wr[IDLE] <= 1'b1;
		state_wr <= 2'b0;
		state_wr[IDLE] <= 1'b1;
	end else begin
	case (1'b1)
		state_wr[IDLE]: begin
			if (wreq) begin
				ACM[waddr] = wdata;
				wack <= 1'b1;
				state_wr[IDLE] <= 1'b0;
				state_wr[ACCESSING] <= 1'b1;
			end
		end
		state_wr[ACCESSING]: begin
			if (!wreq) begin
				wack <= 1'b0;
				state_wr[IDLE] <= 1'b1;
				state_wr[ACCESSING] <= 1'b0;
			end
		end
	endcase
	end
end

always @(rreq or reset) begin
	if (reset) begin
		rack <= 1'b0;
		next_rd <= 2'b0;
		next_rd[IDLE] <= 1'b1;
		state_rd <= 2'b0;
		state_rd[IDLE] <= 1'b1;
	end else begin
	case (1'b1)
		state_rd[IDLE]: begin
			if (rreq) begin
				rdata = ACM[raddr];
				rack <= 1'b1;
				state_rd[IDLE] <= 1'b0;
				state_rd[ACCESSING] <= 1'b1;
			end
		end
		state_rd[ACCESSING]: begin
			if (!rreq) begin
				rack <= 1'b0;
				state_rd[IDLE] <= 1'b1;
				state_rd[ACCESSING] <= 1'b0;
			end
		end
	endcase
	end
end

endmodule
