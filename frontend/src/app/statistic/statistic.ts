import {User} from "../user/user";
import {Word} from "../word/word";

export interface Statistic {
  statisticId: bigint;
  numberOfTries: number;
  user: User;
  correctWord: Word;
}
