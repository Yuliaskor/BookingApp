import { create } from 'zustand';

interface ReserveModalStore {
  isOpen: boolean;
  checkInDate: string;
  checkOutDate: string;
  listingId: number;
  onOpen: (checkInDate: string, checkOutDate: string, listingId: number) => void;
  onClose: () => void;
}

const useReserveModal = create<ReserveModalStore>((set) => ({
  isOpen: false,
  checkInDate: "",
  checkOutDate: "",
  listingId: 0,
  onOpen: (checkInDate, checkOutDate, listingId) => set({ isOpen: true, checkInDate, checkOutDate, listingId }),
  onClose: () => set({ isOpen: false })
}));


export default useReserveModal;