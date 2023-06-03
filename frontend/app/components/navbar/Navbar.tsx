//import { SafeUser } from "@/app/types";

import Categories from "./Categories";
import Container from "../Container";
import Search from "./Search";
import UserMenu from "./UserMenu";


const Navbar = () => {
  return ( 
    <div className="fixed w-full bg-white z-10 shadow-sm">
      <div
        className="
          py-4 
          border-b-[1px]
        "
      >
      <Container>
        <div 
          className="
            flex 
            flex-row 
            items-center 
            justify-between
            gap-3
            md:gap-0
          "
        >
          <span></span>
          <Search />
          <UserMenu />
        </div>
      </Container>
    </div>
    <Categories />
  </div>
  );
}


export default Navbar;