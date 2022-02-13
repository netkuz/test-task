CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public.model
(
    id UUID PRIMARY KEY,
    model_name VARCHAR NOT NULL UNIQUE,
    description VARCHAR
);

COMMENT ON TABLE public.model IS 'Model information';
COMMENT ON COLUMN public.model.id IS 'Model ID';
COMMENT ON COLUMN public.model.model_name IS 'Model name';
COMMENT ON COLUMN public.model.description IS 'Description';

CREATE TABLE IF NOT EXISTS public.program
(
    id UUID PRIMARY KEY,
    program_name VARCHAR NOT NULL,
    model_id UUID REFERENCES public.model (id) ON DELETE CASCADE,
    temperature INTEGER,
    detergent INTEGER NOT NULL DEFAULT 0,
    weight INTEGER,
    spin INTEGER,
    description VARCHAR,
    CONSTRAINT uk_name_model UNIQUE (program_name, model_id)
);

COMMENT ON TABLE public.program IS 'Washing program information';
COMMENT ON COLUMN public.program.id IS 'Program ID';
COMMENT ON COLUMN public.program.program_name IS 'Serial number';
COMMENT ON COLUMN public.program.temperature IS 'Temperature';
COMMENT ON COLUMN public.program.detergent IS 'Amount of detergent';
COMMENT ON COLUMN public.program.weight IS 'Weight (g)';
COMMENT ON COLUMN public.program.spin IS 'Amount of rotation (rpm)';
COMMENT ON COLUMN public.program.description IS 'Description';

CREATE TABLE IF NOT EXISTS public.machine
(
    id UUID PRIMARY KEY,
    serial_number VARCHAR NOT NULL UNIQUE,
    model_id UUID REFERENCES public.model (id) ON DELETE CASCADE,
    program_id UUID REFERENCES public.program (id) ON DELETE SET NULL ON UPDATE CASCADE,
    status INTEGER NOT NULL DEFAULT 0,
    mode INTEGER NOT NULL DEFAULT 0,
    type_ INTEGER NOT NULL DEFAULT 0
);

COMMENT ON TABLE public.machine IS 'Washing machine information';
COMMENT ON COLUMN public.machine.id IS 'Washing machine ID';
COMMENT ON COLUMN public.machine.serial_number IS 'Serial number';
COMMENT ON COLUMN public.machine.model_id IS 'Model ID';
COMMENT ON COLUMN public.machine.program_id IS 'Program ID';
COMMENT ON COLUMN public.machine.status IS 'Status';
COMMENT ON COLUMN public.machine.mode IS 'Mode';
COMMENT ON COLUMN public.machine.type_ IS 'Type';
;;
