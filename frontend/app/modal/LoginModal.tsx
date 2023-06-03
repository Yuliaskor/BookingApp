'use client';

import axios from "axios";
import { useCallback, useState } from "react";
import Modal from './Modal';
import Input from "../inputs/Input";
import Heading from "../Heading";
import Button from "../Button";
import { 
    FieldValues, 
    SubmitHandler,
    useForm
  } from "react-hook-form";



import useLoginModal from "@/app/hooks/useLoginModal";
import { toast } from "react-hot-toast";
import { log } from "console";
import useRegisterModal from "../hooks/useRegisterModal";

const LoginModal = () => {

    const registerModal = useRegisterModal();
    const loginModal = useLoginModal();
    const [isLoading, setIsLoading] = useState(false);

    const { 
        register, 
        handleSubmit,
        formState: {
          errors,
        },
      } = useForm<FieldValues>({
        defaultValues: {
          email: '',
          password: ''
        },
      });

      const onSubmit: SubmitHandler<FieldValues> = (data) => {
        setIsLoading(true);
    
        axios.post('/api/login', data)
        .then(() => {
          toast.success('Logged in');
          registerModal.onClose();
        })
        .catch((error) => {
            console.log(data);
          toast.error('Something Went Wrong');
        })
        .finally(() => {
          setIsLoading(false);
        })
      }

      const onToggle = useCallback(() => {
        loginModal.onClose();
        registerModal.onOpen();
      }, [loginModal, registerModal])
      const bodyContent = (
        <div className="flex flex-col gap-4">
          <Heading
            title="Welcome back"
            subtitle="Login to your account!"
          />
          <Input
            id="email"
            label="Email"
            disabled={isLoading}
            register={register}  
            errors={errors}
            required
          />
          <Input
            id="password"
            label="Password"
            type="password"
            disabled={isLoading}
            register={register}
            errors={errors}
            required
          />
        </div>
      )
    
      const footerContent = (
        <div className="flex flex-col gap-4 mt-3">
          <hr />
          <div className="
          text-neutral-500 text-center mt-4 font-light">
            <p>First time using Airbnb?
              <span 
                onClick={onToggle} 
                className="
                  text-neutral-800
                  cursor-pointer 
                  hover:underline
                "
                > Create an account</span>
            </p>
          </div>
        </div>
      )
    
    return (
      <Modal
      disabled={isLoading}
      isOpen={loginModal.isOpen}
      title="Login"
      actionLabel="Continue"
      onClose={loginModal.onClose}
      onSubmit={handleSubmit(onSubmit)}
      body={bodyContent}
      footer={footerContent}
      />
    )
}

export default LoginModal;