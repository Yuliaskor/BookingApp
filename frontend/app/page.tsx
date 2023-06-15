import Navbar from '@/app/components/navbar/Navbar';
import Container from './components/Container';
import ClientOnly from './ClientOnly';
import EmptyState from './components/EmptyState';
import getListings from './action/getListings';
import ListingCard from './components/listings/ListingCard';
import getCurrentUser from './action/getCurrentUser';
export default async function Home() {
  const listings = await getListings();
  const currentUser = await getCurrentUser();
  if(listings.length == 0){
    return (
      <ClientOnly>
        <EmptyState showReset  />
      </ClientOnly>
    )
  }
  return (
    <ClientOnly>
    <Container>
      <div 
        className="
          pt-24
          grid 
          grid-cols-1 
          sm:grid-cols-2 
          md:grid-cols-3 
          lg:grid-cols-4
          xl:grid-cols-5
          2xl:grid-cols-6
          gap-8
        "
      >
       {listings.map((listing: any) => (
            <ListingCard
              currentUser={currentUser}
              key={listing.id}
              data={listing}
            />
          ))}

        </div>
        

    </Container>
  </ClientOnly>
  )
}
