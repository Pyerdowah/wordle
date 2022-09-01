import {LoginStatus} from "./loginStatus";

export interface User{
  wordId: bigint;
  login: string;
  password: string;
  status: LoginStatus;
}
